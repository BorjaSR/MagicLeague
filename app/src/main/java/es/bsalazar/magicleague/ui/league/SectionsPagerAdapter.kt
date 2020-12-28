package es.bsalazar.magicleague.ui.league

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionsPagerAdapter(
    private val fragments: List<Pair<String, Fragment>>,
    fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments[position].second
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments[position].first
    }

    override fun getCount(): Int {
        return fragments.size
    }
}