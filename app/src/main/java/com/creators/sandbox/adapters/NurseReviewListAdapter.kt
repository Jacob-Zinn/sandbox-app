package com.creators.sandbox.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.creators.sandbox.databinding.PreviewDoctorListItemBinding
import com.creators.sandbox.models.Doctor

class PreviewDoctorListAdapter constructor(
    private val interaction: Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val LIST_ITEM = 1


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Doctor>() {


        override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
            return oldItem.id == oldItem.id
        }

    }

    private val differ =
        AsyncListDiffer(
            FeedRecyclerChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    fun submitList(
        list: MutableList<Doctor>
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
                val itemBinding = PreviewDoctorListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return DoctorItem(
                    itemBinding,
                    interaction = interaction,
                )
            }
            else -> {
                val itemBinding = PreviewDoctorListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return DoctorItem(
                    itemBinding,
                    interaction = interaction
                )
            }

        }
    }

    internal inner class FeedRecyclerChangeCallback(
        private val adapter: PreviewDoctorListAdapter
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
            is DoctorItem ->  holder.bind(differ.currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return LIST_ITEM
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    // This class allows user items a dividing line beneath
    class DoctorItem
    constructor(
        private val itemBinding: PreviewDoctorListItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Doctor) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(bindingAdapterPosition, item, "")
            }
            itemBinding.nameFirst.text = item.first_name
            itemBinding.nameLast.text = item.last_name
            itemBinding.nurseDesc.text = item.doctor_desciption_1
            itemBinding.yearsExpert.text = item.years_experience.toString()
            itemBinding.qualities.text = "${item.doctor_qualities_1}, ${item.doctor_qualities_2}, and ${item.doctor_qualities_3}"
            itemBinding.numberNurseCert.text = ((item.id * 7) % 5).toString()
            itemBinding.industryExpertise.text = "${item.expertise_1}, ${item.expertise_2}, and ${item.expertise_3}"

        }
    }


    interface Interaction {

        fun onItemSelected(position: Int, item: Doctor, tag: String)

        fun restoreListPosition()
    }
}
