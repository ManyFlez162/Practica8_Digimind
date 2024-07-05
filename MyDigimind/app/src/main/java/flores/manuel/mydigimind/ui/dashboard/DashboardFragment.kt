package flores.manuel.mydigimind.ui.dashboard

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import flores.manuel.mydigimind.R
import flores.manuel.mydigimind.databinding.FragmentDashboardBinding
import flores.manuel.mydigimind.ui.Task
import flores.manuel.mydigimind.ui.home.HomeFragment
import kotlinx.coroutines.selects.selectUnbiased
import java.text.SimpleDateFormat

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val database = FirebaseFirestore.getInstance()
    private val collection = database.collection("actividades")

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        // Probando repositorio para nueva rama
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Aquí descarté usar todo esto porque nuestra versión de la primera parte era diferente a la del maestro, y respeté más el trabajo que ya teníamos

//        val btn_time: Button = root.findViewById(R.id.done)
//
//        btn_time.setOnClickListener {
//            val cal = Calendar.getInstance()
//            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
//                cal.set(Calendar.HOUR_OF_DAY, hour)
//                cal.set(Calendar.MINUTE, minute)
//
//                btn_time.text = SimpleDateFormat("HH:mm").format(cal.time)
//            }
//
//            TimePickerDialog(root.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
//
//        }

        val btn_save: Button = root.findViewById(R.id.done)
        val et_titulo: EditText = root.findViewById(R.id.name)
        val tiempo: EditText = root.findViewById(R.id.time)
        val checkMonday: CheckBox = root.findViewById(R.id.monday)
        val checkTuesday: CheckBox = root.findViewById(R.id.tuesday)
        val checkWednesday: CheckBox = root.findViewById(R.id.wednesday)
        val checkThursday: CheckBox = root.findViewById(R.id.thursday)
        val checkFriday: CheckBox = root.findViewById(R.id.friday)
        val checkSaturday: CheckBox = root.findViewById(R.id.saturday)
        val checkSunday: CheckBox = root.findViewById(R.id.sunday)

        btn_save.setOnClickListener {
            var days = ArrayList<String>()
            var title = et_titulo.text.toString()
            var time = tiempo.text.toString()
            var lu: Boolean = false
            var ma: Boolean = false
            var mi: Boolean = false
            var ju: Boolean = false
            var vi: Boolean = false
            var sa: Boolean = false
            var dom: Boolean = false

            if(checkMonday.isChecked){
                days.add("Monday")
                lu = true
            }
            if(checkTuesday.isChecked){
                days.add("Tuesday")
                ma = true
            }
            if(checkWednesday.isChecked){
                days.add("Wednesday")
                mi = true
            }
            if(checkThursday.isChecked){
                days.add("Thursday")
                ju = true
            }
            if(checkFriday.isChecked){
                days.add("Friday")
                vi = true
            }
            if(checkSaturday.isChecked){
                days.add("Saturday")
                sa = true
            }
            if(checkSunday.isChecked){
                days.add("Sunday")
                dom = true
            }

            var task = Task(title, days, time, lu, ma, mi, ju, vi, sa, dom)

            HomeFragment.tasks.add(task)

            collection.add(task).addOnSuccessListener { documentReference ->
                Toast.makeText(root.context, "se agregó a la base de datos", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { e ->
                Toast.makeText(root.context, "no se agregó a la base", Toast.LENGTH_SHORT).show()
            }

            Toast.makeText(root.context, "new task added", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}