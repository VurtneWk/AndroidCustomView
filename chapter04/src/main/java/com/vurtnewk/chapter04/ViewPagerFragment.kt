package com.vurtnewk.chapter04

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vurtnewk.chapter04.databinding.FragmentViewPagerBinding

/**
 * createTime:  2024/12/31 14:47
 * author:      vurtnewk
 * description:
 */
class ViewPagerFragment : Fragment() {

    companion object {
        fun newInstance(content: String): ViewPagerFragment {
            val args = Bundle()
            args.putString("content", content)
            val fragment = ViewPagerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mFragmentViewPagerBinding: FragmentViewPagerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mFragmentViewPagerBinding = FragmentViewPagerBinding.inflate(layoutInflater)
        return mFragmentViewPagerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val content = arguments?.getString("content")
        mFragmentViewPagerBinding.tvContent.text = content
    }
}