package es.bsalazar.magicleague.ui.dashboard

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import es.bsalazar.magicleague.R

class CreateLeagueDialog(val onClickCreate: (String) -> Unit) : DialogFragment() {

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
//        dialog?.window?.attributes?.windowAnimations = R.style.CommentsDialogAnimation
        dialog?.setCancelable(true)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_league_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<LinearLayout>(R.id.create_league_button).setOnClickListener {
            onClickCreate(view.findViewById<EditText>(R.id.et_league_name).text.toString())
            dismiss()
        }
    }
}