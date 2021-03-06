package sebastiani.app_rondon.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import sebastiani.app_rondon.adapters.DashboardItemsListAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_dashboard.*
import sebastiani.app_rondon.Firebase.FirestoreClass
import sebastiani.app_rondon.Login.LoginActivity
import sebastiani.app_rondon.Model.Product
import sebastiani.app_rondon.R


class DashboardFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

//            R.id.action_settings -> {
//
//                startActivity(Intent(activity, SettingsActivity::class.java))
//                return true
//            }
            R.id.action_cart -> {

                startActivity(Intent(activity, CartListActivity::class.java))
                return true
            }
            R.id.log_out_atlternative -> {

                FirebaseAuth.getInstance().signOut()

                startActivity(Intent(activity, LoginActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        getDashboardItemsList()
    }

    private fun getDashboardItemsList() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getDashboardItemsList(this@DashboardFragment)
    }


    fun successDashboardItemsList(dashboardItemsList: ArrayList<Product>) {

        // Hide the progress dialog.
        hideProgressDialog()

        if (dashboardItemsList.size > 0) {

            rv_dashboard_items.visibility = View.VISIBLE
            tv_no_dashboard_items_found.visibility = View.GONE

            rv_dashboard_items.layoutManager = GridLayoutManager(activity, 2)
            rv_dashboard_items.setHasFixedSize(true)

            val adapter = DashboardItemsListAdapter(requireActivity(), dashboardItemsList)
            rv_dashboard_items.adapter = adapter
        } else {
            rv_dashboard_items.visibility = View.GONE
            tv_no_dashboard_items_found.visibility = View.VISIBLE
        }
    }
}