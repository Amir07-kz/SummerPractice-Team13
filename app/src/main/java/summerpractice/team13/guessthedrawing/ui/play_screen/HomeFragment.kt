package summerpractice.team13.guessthedrawing.ui.play_screen

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputLayout
import summerpractice.team13.guessthedrawing.R
import summerpractice.team13.guessthedrawing.mvp.presenters.AppPreferences
import summerpractice.team13.guessthedrawing.mvp.presenters.answer_check_presenter.AnswerCheckPresenter
import summerpractice.team13.guessthedrawing.mvp.presenters.answer_check_presenter.IAnswerCheckPresenter
import summerpractice.team13.guessthedrawing.mvp.views.answer_check_view.IAnswerCheckView
import summerpractice.team13.guessthedrawing.utils.LocaleUtils


class HomeFragment : Fragment(), IAnswerCheckView {

    private lateinit var ianswerCheckPresenter: IAnswerCheckPresenter
    private lateinit var root: View




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        context?.let { AppPreferences.init(it) }

        context?.let { LocaleUtils.setLocaleLanguage(AppPreferences.language.orEmpty(), it) }
        root = inflater.inflate(R.layout.fragment_play, container, false)

        // Values
        val guessButton: Button = root.findViewById(R.id.btn_guess)
        val answerEditText: EditText = root.findViewById(R.id.et_answer)
        val answerTextInput: TextInputLayout = root.findViewById(R.id.ti_answer)
        val pictureImageView: ImageView = root.findViewById(R.id.iv_picture)
        val progressIndicator: LinearProgressIndicator = root.findViewById(R.id.progress_indicator)
        val chronometer: Chronometer = root.findViewById(R.id.chronometer)
        val universalButton: Button = root.findViewById(R.id.btn_universal)
        val coinImageView: ImageView = root.findViewById(R.id.iv_coin)
        val testTextView: TextView = root.findViewById(R.id.testTextView)
        val mainSeparator: View = root.findViewById(R.id.separator_main)

        // Default
        elementsNotVisible(
            guessButton,
            answerEditText,
            answerTextInput,
            pictureImageView,
            progressIndicator,
            testTextView,
            universalButton,
            mainSeparator,
            coinImageView
        )

        // По нажатию кнопки на клавиатуре автоматически нажимается кнопка "Guess"
        answerEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                guessButton.performClick()

            }
            false
        }

        ianswerCheckPresenter = AnswerCheckPresenter(this)
        guessButton.setOnClickListener {
            context?.applicationContext?.let { it1 ->
                ianswerCheckPresenter.checkAnswer(
                    it1,
                    answerEditText.text.toString().lowercase(),
                    resources.getResourceEntryName(
                        ianswerCheckPresenter.getDrawableId(
                            pictureImageView
                        )
                    ),
                    pictureImageView,
                    answerEditText,
                    chronometer,
                    progressIndicator,
                    guessButton
                )
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                chronometer.isCountDown = true
            }
        }

        chronometer.setOnChronometerTickListener {
            val elapsedMillis: Long = chronometer.base - SystemClock.elapsedRealtime()

            testTextView.text = (elapsedMillis / 1000).toString()
            progressIndicator.progress = (elapsedMillis / 1000).toInt()

            if (progressIndicator.progress <= 0) {
                // останавливает хронометер
                chronometer.stop()

                // задержка в 1 секунду
                val handler = Handler()
                handler.postDelayed(Runnable {

                    elementsNotVisible(
                        guessButton,
                        answerEditText,
                        answerTextInput,
                        pictureImageView,
                        progressIndicator,
                        testTextView,
                        universalButton,
                        mainSeparator,
                        coinImageView
                    )
                    // меняет текст кнопки "Start"
                    universalButton.text = getString(R.string.try_again)

                }, 1000)
            }
        }

        universalButton.setOnClickListener {
            elementsVisible(
                guessButton,
                answerEditText,
                answerTextInput,
                pictureImageView,
                progressIndicator,
                testTextView,
                universalButton,
                mainSeparator,
                coinImageView
            )

            // Показывает рандомный рисунок
            ianswerCheckPresenter.getRandomPicture(pictureImageView)

            // Запускает хронометер
            chronometer.base = SystemClock.elapsedRealtime() + AppPreferences.time * 1000
            chronometer.start()
            progressIndicator.max = AppPreferences.time

            // Очищает поле
            answerEditText.text.clear()
        }

        return root
    }

    private fun elementsVisible(
        guessButton: Button,
        answerEditText: EditText,
        answerTextInput: TextInputLayout,
        pictureImageView: ImageView,
        progressIndicator: LinearProgressIndicator,
        testTextView: TextView,
        testFixButton: Button,
        mainSeparator: View,
        coinImageView: ImageView
    ) {

        guessButton.isVisible = true
        answerEditText.isVisible = true
        answerTextInput.isVisible = true
        pictureImageView.isVisible = true
        progressIndicator.isVisible = true
        testTextView.isVisible = true
        mainSeparator.isVisible = true
        coinImageView.isVisible = true

        testFixButton.isVisible = false
    }

    private fun elementsNotVisible(
        guessButton: Button,
        answerEditText: EditText,
        answerTextInput: TextInputLayout,
        pictureImageView: ImageView,
        progressIndicator: LinearProgressIndicator,
        testTextView: TextView,
        testFixButton: Button,
        mainSeparator: View,
        coinImageView: ImageView
    ) {

        guessButton.isVisible = false
        answerEditText.isVisible = false
        answerTextInput.isVisible = false
        pictureImageView.isVisible = false
        progressIndicator.isVisible = false
        testTextView.isVisible = false
        mainSeparator.isVisible = false
        coinImageView.isVisible = false

        testFixButton.isVisible = true
    }

    override fun onPause() {
        super.onPause()
        println("from onPause")
    }

    override fun onStop() {
        super.onStop()
        println("from onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("from onDestroyView")
    }


    override fun onDestroy() {
        super.onDestroy()
        println("from onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        println("from onDetach")
    }

    override fun showTrueIcon(context: Context) {
        val inflater = layoutInflater

        val views = inflater.inflate(R.layout.green_toast_item, null)
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = views

        toast.show()

    }

    override fun showFalseIcon(context: Context) {
        val inflater = layoutInflater

        val views = inflater.inflate(R.layout.red_toast_item, null)
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = views

        toast.show()

    }
}