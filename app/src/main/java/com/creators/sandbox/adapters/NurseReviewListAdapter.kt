package com.creators.sandbox.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.creators.sandbox.databinding.LayoutNurseReviewItemBinding

class NurseReviewListAdapter constructor(
    private val interaction: Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val LIST_ITEM = 1


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {


        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == oldItem
        }

    }

    private val differ =
        AsyncListDiffer(
            FeedRecyclerChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    fun submitList(
        list: MutableList<String>
    ) {
        val newList = list?.toMutableList()

        val commitCallback = Runnable {
            // if process died must restore list position
            // very annoying
            interaction?.restoreListPosition()
        }
        differ.submitList(newList, commitCallback)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {

            LIST_ITEM -> {
                val itemBinding = LayoutNurseReviewItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return NurseReview(
                    itemBinding,
                    interaction = interaction,
                )
            }
            else -> {
                val itemBinding = LayoutNurseReviewItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return NurseReview(
                    itemBinding,
                    interaction = interaction
                )
            }

        }
    }

    internal inner class FeedRecyclerChangeCallback(
        private val adapter: NurseReviewListAdapter
    ) : ListUpdateCallback {

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NurseReview ->  holder.bind(differ.currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return LIST_ITEM
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    // This class allows user items a dividing line beneath
    class NurseReview
    constructor(
        private val itemBinding: LayoutNurseReviewItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: String) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(bindingAdapterPosition, item)
            }
            itemBinding.desciption.text = item
        }
    }


    interface Interaction {

        fun onItemSelected(position: Int, item: String)

        fun restoreListPosition()
    }
}
