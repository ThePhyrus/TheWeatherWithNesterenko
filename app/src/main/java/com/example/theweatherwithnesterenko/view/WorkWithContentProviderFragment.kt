package com.example.theweatherwithnesterenko.view


import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.theweatherwithnesterenko.databinding.FragmentWorkWithContentProviderBinding
import com.example.theweatherwithnesterenko.utils.REQUEST_CODE_CALL
import com.example.theweatherwithnesterenko.utils.REQUEST_CODE_CONTACTS
import com.example.theweatherwithnesterenko.utils.TAG


class WorkWithContentProviderFragment : Fragment() {

    private var _binding: FragmentWorkWithContentProviderBinding? = null
    private val binding: FragmentWorkWithContentProviderBinding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkWithContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_CONTACTS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    getContacts()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    explain()
                }
                else -> {
                    myRequestPermission()
                }
            }
        }
    }


    private fun explain() {
        AlertDialog.Builder(requireContext())
            .setTitle("Нужен доступ к контактам")
            .setMessage("Объяснение: Вот зачем нам нужен доступ к контактам")
            .setPositiveButton("Предоставить доступ") { _, _ ->
                myRequestPermission()
            }
            .setNegativeButton("Не предоставлять") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun getContacts() {//FIXME
        context?.let { it ->
            val contentResolver = it.contentResolver
            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
            cursor?.let { cursor ->
                for (i in 0 until cursor.count) {
                    cursor.moveToPosition(i)
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))//FIXME
                    val contactId =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))//FIXME
                    val number = getNumberFromID(contentResolver, contactId)
                    addView(name, number)
                }
            }
            cursor?.close()
        }
    }


    private fun myRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE_CONTACTS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_CONTACTS) {
            when {
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> {
                    getContacts()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    explain()
                }
                else -> {
                    Log.d(TAG, "onRequestPermissionsResult: $requestCode")
                }
            }
        } else if (requestCode == REQUEST_CODE_CALL) {
            when {
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> {
                    makeCall()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    explain()
                }
                else -> {
                    makeCall()
                }
            }
        }
    }


    @SuppressLint("Range")
    private fun getNumberFromID(cr: ContentResolver, contactId: String): String {
        val phones = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null
        )
        var number = "none"
        phones?.let { cursor ->
            while (cursor.moveToNext()) {
                number =
                    cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    )
            }
        }
        return number
    }


    private fun addView(name: String, number: String) {
        binding.containerForContacts.addView(TextView(requireContext()).apply {
            text = "$name:$number"
            textSize = 30f
            setOnClickListener {
                numberCurrent = number
                makeCall()
            }
        })
    }


    private var numberCurrent: String = "none"
    private fun makeCall() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$numberCurrent"))
            startActivity(intent)
        } else {
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE_CALL)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WorkWithContentProviderFragment()
    }

}





