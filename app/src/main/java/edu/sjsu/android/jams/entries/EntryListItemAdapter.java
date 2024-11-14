package edu.sjsu.android.jams.entries;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.sjsu.android.jams.placeholder.PlaceholderContent.PlaceholderItem;
import edu.sjsu.android.jams.databinding.FragmentEntryItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class EntryListItemAdapter extends RecyclerView.Adapter<EntryListItemAdapter.ViewHolder> {

    private final List<String> mValues;

    public EntryListItemAdapter(List<String> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentEntryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Get the current data from the arraylist based on the position
        String current = mValues.get(position);
//        holder.binding.entryDate.setText(current);
//        holder.binding.entryTitle.setText(current);
//        holder.binding.entryContentPreview.setText(current);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected final FragmentEntryItemBinding binding;
        public ViewHolder(FragmentEntryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // The root represent one row
            // When the row is clicked, a toast popup
            this.binding.getRoot().setOnClickListener(view ->
                    Toast.makeText(view.getContext(), "A row clicked",
                            Toast.LENGTH_SHORT).show());
        }
    }
}