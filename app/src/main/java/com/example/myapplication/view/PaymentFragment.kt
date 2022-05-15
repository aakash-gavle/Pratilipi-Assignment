package com.example.myapplication.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.model.DataModel
import com.example.myapplication.viewmodel.MyViewModel
import com.google.firebase.database.FirebaseDatabase


class PaymentFragment : Fragment() {

    lateinit var textEmail:TextView
    lateinit var amountEditText: EditText
    lateinit var paymentBtn: Button
    lateinit var balanceBtn: Button
    lateinit var balanceTv: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_payment, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        val database = FirebaseDatabase.getInstance().getReference("Payment")

        textEmail=view.findViewById(R.id.txtEmail)
        amountEditText=view.findViewById(R.id.amountEditText)
        paymentBtn=view.findViewById(R.id.paymentBtn)
        balanceBtn=view.findViewById(R.id.balanceBtn)
        balanceTv=view.findViewById(R.id.balanceTv)

        viewModel.email.observe(viewLifecycleOwner, Observer {
            textEmail.text=it
        })

        paymentBtn.setOnClickListener {
            if(TextUtils.isEmpty(amountEditText.text.toString())){
                Toast.makeText(context,"Please enter amount",Toast.LENGTH_SHORT).show()
            }
            else{
                val email=textEmail.text.toString()
                var balance=balanceTv.text.toString().toInt()
                val amount=amountEditText.text.toString().toInt()

                val paymentId=database.push().key!!
                if(balance<amount){
                    Toast.makeText(context,"Current balance is low",Toast.LENGTH_LONG).show()
                }else if (balance>=amount){
                    val data=DataModel(paymentId,email,(balance.toString().toInt()-amount.toString().toInt()).toString(),amount.toString())
                    database.child(paymentId).setValue(data)
                        .addOnCompleteListener {
                            Toast.makeText(context,"Payment successful",Toast.LENGTH_LONG).show()
                            balance -= amount
                            amountEditText.text.clear()
                            balanceTv.text=balance.toString()
                        }.addOnFailureListener {
                            Toast.makeText(context,"Payment Failed",Toast.LENGTH_LONG).show()
                        }
                }
            }
        }

        balanceBtn.setOnClickListener {
            balanceTv.isVisible=true
        }

        return view
    }

}