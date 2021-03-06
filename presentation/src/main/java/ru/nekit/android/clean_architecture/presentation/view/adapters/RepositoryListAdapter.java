package ru.nekit.android.clean_architecture.presentation.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.nekit.android.clean_architecture.R;
import ru.nekit.android.clean_architecture.presentation.core.view.adapter.BaseAdapter;
import ru.nekit.android.clean_architecture.presentation.model.vo.RepositoryVO;
import ru.nekit.android.clean_architecture.presentation.presenter.RepositoryListPresenter;

public class RepositoryListAdapter extends BaseAdapter<RepositoryVO, RepositoryListAdapter.ViewHolder> {

    private WeakReference<RepositoryListPresenter> presenterRef;

    public RepositoryListAdapter(RepositoryListPresenter presenter) {
        this.presenterRef = new WeakReference<>(presenter);
    }

    public void setRepositoryList(List<RepositoryVO> repositoryList) {
        this.list = repositoryList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        RepositoryVO repository = list.get(i);
        viewHolder.titleView.setText(repository.getRepoName());
        viewHolder.descriptionView.setText(repository.getDescription());
        viewHolder.starsView.setText(repository.getStarsCount());
        viewHolder.forksView.setText(repository.getForksCount());
        viewHolder.watchersView.setText(repository.getWatchersCount());
        if (presenterRef != null && presenterRef.get() != null) {
            viewHolder.rootView.setOnClickListener(view -> presenterRef.get().selectRepository(repository));
        }
    }

    public void destroy() {
        if (presenterRef != null) {
            presenterRef.clear();
        }
        presenterRef = null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_repository_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends BaseAdapter.ViewHolder {

        private final TextView titleView;
        private final TextView descriptionView;
        private final TextView starsView;
        private final TextView watchersView;
        private final TextView forksView;
        private final View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.text_title);
            descriptionView = (TextView) itemView.findViewById(R.id.text_description);
            starsView = (TextView) itemView.findViewById(R.id.text_stars);
            watchersView = (TextView) itemView.findViewById(R.id.text_watchers);
            forksView = (TextView) itemView.findViewById(R.id.text_forks);
            rootView = itemView.findViewById(R.id.card_view);
        }
    }
}