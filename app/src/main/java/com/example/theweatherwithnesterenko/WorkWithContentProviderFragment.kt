package com.example.theweatherwithnesterenko


import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.theweatherwithnesterenko.databinding.FragmentWorkWithContentProviderBinding
import com.example.theweatherwithnesterenko.utils.REQUEST_CODE_FOR_PERMISSION_TO_READ_USER_CONTACTS


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
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getContacts()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            explainNecessityReadContacts()
        } else {
            mRequestPermission()
        }
    }

    private fun explainNecessityReadContacts() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.access_to_contacts_ad_title)
            .setMessage(R.string.description)
            .setPositiveButton(R.string.allow_access) { _, _ ->
                mRequestPermission()
            }
            .setNegativeButton(R.string.close_access) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun mRequestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.READ_CONTACTS),
            REQUEST_CODE_FOR_PERMISSION_TO_READ_USER_CONTACTS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_FOR_PERMISSION_TO_READ_USER_CONTACTS) {

            for (i in permissions.indices) {
                if (permissions[i] == Manifest.permission.READ_CONTACTS && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    getContacts()
                } else {
                    explainNecessityReadContacts()
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun getContacts() {
        val contentResolver: ContentResolver = requireContext().contentResolver

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )
        cursor?.let {
            for (i in 0 until it.count) {
                if (cursor.moveToPosition(i)) {
                    val columnNameIndex =
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    val name: String = cursor.getString(columnNameIndex)
                    binding.ContainerForContacts.addView(TextView(requireContext()).apply {
                        textSize = 30f
                        text = name
                    })
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WorkWithContentProviderFragment()
    }

}





