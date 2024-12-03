package edu.sjsu.android.jams.entries;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.sjsu.android.jams.R;
import edu.sjsu.android.jams.databinding.FragmentEntryContentItemBinding;

import java.util.List;

// Adapter for Journal Entry Item
public class EntryListItemContentAdapter extends RecyclerView.Adapter<EntryListItemContentAdapter.ViewHolder> {
    private Fragment fragment;
    private final List<Entry> mValues;

    /**
     * Constructs the Adapter, which consists of a list of journal entries.
     * @param items a List of journal entries
     */
    public EntryListItemContentAdapter(Fragment fragment, List<Entry> items) {
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
        return new ViewHolder(FragmentEntryContentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

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
        Entry current = mValues.get(position);

        // Set entry id
        holder.binding.entryId.setText(String.valueOf(current.getEntryID()));
        // Set entry title
        holder.binding.entryTitle.setText(current.getTitle());
        // Set entry content
        holder.binding.entryContentPreview.setText(current.getContent());

        //Pass bundle on navigation
        Bundle bundle = new Bundle();
        bundle.putParcelable(fragment.getString(R.string.entry_key), current);

        holder.binding.getRoot().setOnClickListener(view -> {
            NavController controller = NavHostFragment.findNavController(fragment);
            controller.navigate(R.id.action_entryListFragment_to_makeEntryFragment, bundle);
        });

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
        protected final FragmentEntryContentItemBinding binding;

        /**
         * A Wrapper around a View that contains the layout (UI) for each row in the list.
         * @param binding
         */
        public ViewHolder(FragmentEntryContentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // The root represent one row
            // When the row is clicked, a toast popup
            this.binding.getRoot().setOnClickListener(view ->
                    Toast.makeText(view.getContext(), "An entry clicked",
                            Toast.LENGTH_SHORT).show());
        }
    }
}