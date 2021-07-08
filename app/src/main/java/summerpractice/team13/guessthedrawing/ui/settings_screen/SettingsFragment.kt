package summerpractice.team13.guessthedrawing.ui.settings_screen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputLayout
import summerpractice.team13.guessthedrawing.R
import summerpractice.team13.guessthedrawing.databinding.FragmentSettingsBinding
import summerpractice.team13.guessthedrawing.mvp.presenters.AppPreferences
import summerpractice.team13.guessthedrawing.mvp.presenters.change_time_presenter.ChangeDifficultyPresenter
import summerpractice.team13.guessthedrawing.mvp.presenters.change_time_presenter.IChangeDifficultyPresenter
import summerpractice.team13.guessthedrawing.mvp.views.change_time_view.IChangeDifficultyView
import summerpractice.team13.guessthedrawing.utils.LocaleUtils


class SettingsFragment : Fragment(), IChangeDifficultyView {

    private lateinit var ichangeDifficultyPresenter: IChangeDifficultyPresenter
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var autoCompleteTextViewLang: AutoCompleteTextView
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var textField: TextInputLayout
    private lateinit var textFieldLang: TextInputLayout


    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        val difficulties = resources.getStringArray(R.array.difficultyLevels)
        arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, difficulties)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        val languages = resources.getStringArray(R.array.languages)
        arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
        binding.autoCompleteTextViewLang.setAdapter(arrayAdapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.let { LocaleUtils.setLocaleLanguage(AppPreferences.language.toString(), it) }

        context?.let { AppPreferences.init(it) }
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        ichangeDifficultyPresenter = ChangeDifficultyPresenter(this)

        val view = binding.root


        val sliderMusic = binding.sliderMusic


        textField = binding.textField
        autoCompleteTextView = textField.editText as AutoCompleteTextView
        autoCompleteTextView.setText(AppPreferences.mode) // default Easy
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            ichangeDifficultyPresenter.switchDifficulty(position)


        }
        textFieldLang = binding.languagesTF
        autoCompleteTextViewLang = textFieldLang.editText as AutoCompleteTextView
        autoCompleteTextViewLang.setText(AppPreferences.language) //default English
        autoCompleteTextViewLang.setOnItemClickListener { _, _, position, _ ->

            if (position == 0) {
                AppPreferences.language = "en"
                val l = AppPreferences.language

                context?.let {
                    if (l != null) {
                        LocaleUtils.setLocaleLanguage(l, it)
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    fragmentManager?.beginTransaction()?.detach(this)?.commit();
                    fragmentManager?.beginTransaction()?.attach(this)?.commit();
                } else {
                    fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit();
                }


            } else if (position == 1) {
                AppPreferences.language = "ru"
                val l = AppPreferences.language
                context?.let {
                    if (l != null) {
                        LocaleUtils.setLocaleLanguage(l, it)
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    fragmentManager?.beginTransaction()?.detach(this)?.commitNow();
                    fragmentManager?.beginTransaction()?.attach(this)?.commitNow();
                } else {
                    fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit();
                }
            }


        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("Test", "CalledOnDestroyView")

    }

    override fun showTimeToast(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
    }


    // Для дебага
    override fun onPause() {
        super.onPause()
        Log.d("Test", "CalledOnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Test", "CalledOnStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Test", "CalledOnDestroy")

    }

    override fun onDetach() {
        super.onDetach()
        Log.d("Test", "CalledOnDetach")

    }


}