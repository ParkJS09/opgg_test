package com.example.opgg_test.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opgg_test.R
import com.example.opgg_test.databinding.FragmentMainBinding
import com.example.opgg_test.presenter.adapter.MainAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(mainViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )
        binding.vm = mainViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMain.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = mainAdapter.itemCount - 1
                    if (lastVisibleItemPosition == itemTotalCount) {
                        mainViewModel.getMoreMatchData()
                    }
                }
            })
        }
        mainViewModel.getAllData()
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { uiState ->
                    when (uiState) {
                        is MainUiState.EmptyInfo -> {
                            if (uiState.isLoading) {
                                Toast.makeText(
                                    requireContext(),
                                    "데이터를 조회 중 입니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            if(uiState.noticeMsg.isNotEmpty()){
                                Toast.makeText(requireContext(),uiState.noticeMsg, Toast.LENGTH_SHORT).show()
                            }
                        }
                        is MainUiState.SummonerInfo -> {
                            if(uiState.noticeMsg.isNotEmpty()){
                                Toast.makeText(requireContext(),uiState.noticeMsg, Toast.LENGTH_SHORT).show()
                            }
                            mainAdapter.submitList(uiState.infoList)
                        }
                    }
                }
        }
    }
}