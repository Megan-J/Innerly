package edu.sjsu.android.jams.entries;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import edu.sjsu.android.jams.databinding.FragmentEntryItemBinding;

import java.util.List;

// Adapter for Journal Entry Item containing Date and Entry List for that Date
public class EntryListItemDateContentAdapter extends RecyclerView.Adapter<EntryListItemDateContentAdapter.ViewHolder> {
    private Fragment fragment;
    private final List<EntryListItemDateContent> mValues;

    /**
     * Constructs the Adapter, which consists of a list of dates and entry Lists items.
     * @param items a List of dates and entry lists
     */
    public EntryListItemDateContentAdapter(Fragment fragment, List<EntryListItemDateContent> items) {
        this.fragment = fragment;
        mValues = items;
    }

    /**
     * Creates a new ViewHolder object for a row when needed.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position. The whole list.
     * @param viewType The view type of the new View. Will be taken care of by the Recycler View.
     * @return a new ViewHolder for a row.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentEntryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    /**
     * Displays the view holder's layout (UI) using the corresponding data at the specified position.
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Get the current data from the arraylist based on the position
        EntryListItemDateContent current = mValues.get(position);
        // Set date
        holder.binding.entryDate.setText(current.getDate());
        // Set adapter for journal entry content
        EntryListItemContentAdapter contentAdapter = new EntryListItemContentAdapter(fragment, current.getEntryList());
        holder.binding.entryListDateAndContentRecyclerView.setLayoutManager(new LinearLayoutManager(holder.binding.entryListDateAndContentRecyclerView.getContext()));
        holder.binding.entryListDateAndContentRecyclerView.setAdapter(contentAdapter);
    }

    /**
     * Returns the number of data (the size of the ArrayList mValues)
     * @return the size of mValues
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected final FragmentEntryItemBinding binding;
        /**
         * A Wrapper around a View that contains the layout (UI) for each row in the list.
         * @param binding
         */
        public ViewHolder(FragmentEntryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}