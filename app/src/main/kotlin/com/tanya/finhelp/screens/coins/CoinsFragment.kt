package com.tanya.finhelp.screens.coins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tanya.finhelp.R
import com.tanya.finhelp.databinding.FragmentCoinsBinding
import com.tanya.finhelp.domain.model.Coin
import com.tanya.finhelp.screens.coinInfo.CoinInfoBottomSheet
import com.tanya.finhelp.screens.coins.adapter.CoinsAdapter
import dagger.hilt.android.AndroidEntryPoint

const val COIN_KEY = "Coin"
private const val TAG_COIN_INFO_BOTTOM_SHEET = "TAG_COIN_INFO_BOTTOM_SHEET"
private const val MENU_INDEX = 0

@AndroidEntryPoint
class CoinsFragment : Fragment(R.layout.fragment_coins) {

    private var _binding: FragmentCoinsBinding? = null
    private val binding get() = _binding!!
    private val vm: CoinsVM by viewModels()
    private val adapter = CoinsAdapter { coin ->
        navigateToCoinInfo(coin)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCoinsBinding.inflate(inflater, container, false)

        bindUI()
        observeVM()

        vm.getCoins()

        return binding.root
    }

    private fun bindUI() {
        binding.recycler.adapter = adapter
        binding.errorLayout.restartButton.setOnClickListener {
            vm.getCoins()
        }
        binding.usernameText.text = vm.getUsername()
        binding.toolbar.menu[MENU_INDEX].setOnMenuItemClickListener {
            vm.deleteUsername()
            findNavController().navigate(R.id.action_coinsFragment_to_authFragment)
            true
        }
    }

    private fun observeVM() {
        vm.state.observe(viewLifecycleOwner) { coins ->
            adapter.submitList(coins)
        }

        vm.errorState.observe(viewLifecycleOwner) { isError ->
            if (isError) adapter.submitList(emptyList())
            binding.errorLayout.root.isVisible = isError
        }
    }

    private fun navigateToCoinInfo(coin: Coin) {
        CoinInfoBottomSheet().apply {
            arguments = bundleOf(COIN_KEY to coin)
        }.show(childFragmentManager, TAG_COIN_INFO_BOTTOM_SHEET)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
