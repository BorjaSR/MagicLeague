package es.bsalazar.magicleague.ui.profile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.models.Deck
import es.bsalazar.magicleague.utils.Constants

class CreateDeckDialog(val onClickCreate: (Deck) -> Unit) : DialogFragment() {

    val colors: ArrayList<Constants.MTG_COLOR> = arrayListOf()

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
//        dialog?.window?.attributes?.windowAnimations = R.style.CommentsDialogAnimation
        dialog?.setCancelable(true)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_deck_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<CheckBox>(R.id.checkBoxWhite)
            .setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) colors.add(Constants.MTG_COLOR.WHITE) else colors.remove(Constants.MTG_COLOR.WHITE)
            }
        view.findViewById<CheckBox>(R.id.checkBoxRed)
            .setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) colors.add(Constants.MTG_COLOR.RED) else colors.remove(Constants.MTG_COLOR.RED)
            }
        view.findViewById<CheckBox>(R.id.checkBoxBlack)
            .setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) colors.add(Constants.MTG_COLOR.BLACK) else colors.remove(Constants.MTG_COLOR.BLACK)
            }
        view.findViewById<CheckBox>(R.id.checkBoxBlue)
            .setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) colors.add(Constants.MTG_COLOR.BLUE) else colors.remove(Constants.MTG_COLOR.BLUE)
            }
        view.findViewById<CheckBox>(R.id.checkBoxGreen)
            .setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) colors.add(Constants.MTG_COLOR.GREEN) else colors.remove(Constants.MTG_COLOR.GREEN)
            }
        view.findViewById<CheckBox>(R.id.checkBoxColorless)
            .setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) colors.add(Constants.MTG_COLOR.COLORLESS) else colors.remove(Constants.MTG_COLOR.COLORLESS)
            }

        view.findViewById<LinearLayout>(R.id.create_deck_button).setOnClickListener {
            val deck = Deck(view.findViewById<EditText>(R.id.et_deck_name).text.toString())
            deck.colors.addAll(colors)
            onClickCreate(deck)
            dismiss()
        }
    }
}