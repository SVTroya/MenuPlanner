package com.troya.menuplanner.adapters.list;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.troya.menuplanner.adapters.list.ExpRecyclerAdapter.ChildViewHolder;
import com.troya.menuplanner.adapters.list.ExpRecyclerAdapter.ParentViewHolder;
import com.troya.menuplanner.adapters.list.ExpRecyclerAdapter.ViewHolder;
import com.troya.menuplanner.R;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpRecyclerAdapter<PVH extends ParentViewHolder, CVH extends ChildViewHolder>
        extends RecyclerView.Adapter<ViewHolder> {

    private static final String TAG = ExpRecyclerAdapter.class.getSimpleName();
    public static final int PARENT = 0;
    public static final int CHILD = 1;

    @DrawableRes
    private static final int EXPAND_TOGGLE = R.drawable.ic_chevron_down;
    @DrawableRes
    private static final int COLLAPSE_TOGGLE = R.drawable.ic_chevron_up;

    protected List<Item> mData;
    private int mSelectedItem = -1;
    private int mPreviouslySelectedItem = -1;

    @IdRes
    private int mToggleId;

    public ExpRecyclerAdapter(List<Item> data) {
        this(data, -1);
    }

    public ExpRecyclerAdapter(List<Item> data, @IdRes int toggleId) {
        mData = data;
        mToggleId = toggleId;
    }

    public int getToggleId() {
        return mToggleId;
    }

    protected int getSelectedItem() {
        return mSelectedItem;
    }

    protected int getPreviouslySelectedItem() {
        return mPreviouslySelectedItem;
    }

    protected void setPreviouslySelectedItem(int previouslySelectedItem) {
        mPreviouslySelectedItem = previouslySelectedItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (layoutInflater != null) {
            switch (viewType) {
                case PARENT:
                    viewHolder = initializeParentViewHolder(layoutInflater, parent);
                    break;

                case CHILD:
                    viewHolder = initializeChildViewHolder(layoutInflater, parent);
                    break;
            }
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Item item = mData.get(position);
        switch (item.getViewType()) {
            case PARENT:
                if (holder instanceof ParentViewHolder) {
                    ParentViewHolder parentViewHolder = (ParentViewHolder) holder;
                    parentViewHolder.setData(item.getValue());
                    parentViewHolder.setToggle(item);

                    parentViewHolder.mViewToggle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (item.getViewType() == PARENT) {
                                if (holder instanceof ParentViewHolder) {
                                    ParentViewHolder parentViewHolder = (ParentViewHolder) holder;
                                    int nextItemPosition = parentViewHolder.getAdapterPosition() + 1;

                                    if (item.getInvisibleChildren() == null) {
                                        List<Item> itemsToHide = new ArrayList<>();

                                        while (nextItemPosition < mData.size() && mData.get(nextItemPosition).getViewType() == CHILD) {
                                            itemsToHide.add(mData.remove(nextItemPosition));
                                        }

                                        item.setInvisibleChildren(itemsToHide);
                                        notifyItemRangeRemoved(nextItemPosition, itemsToHide.size());
                                        parentViewHolder.setToggle(item);
                                    } else {
                                        mData.addAll(nextItemPosition, item.getInvisibleChildren());
                                        notifyItemRangeInserted(nextItemPosition, item.getInvisibleChildren().size());
                                        item.setInvisibleChildren(null);
                                        parentViewHolder.setToggle(item);
                                    }
                                }
                            }
                        }
                    });
                }
                break;

            case CHILD:
                if (holder instanceof ChildViewHolder) {
                    ChildViewHolder childViewHolder = (ChildViewHolder) holder;
                    childViewHolder.setData(item.getValue());
                }
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPreviouslySelectedItem = mSelectedItem;
                mSelectedItem = (mSelectedItem != holder.getAdapterPosition()) ?
                        holder.getAdapterPosition() :
                        -1;
                ExpRecyclerAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mData != null) ? mData.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getViewType();
    }

    abstract PVH initializeParentViewHolder(LayoutInflater layoutInflater, ViewGroup parent);

    abstract CVH initializeChildViewHolder(LayoutInflater layoutInflater, ViewGroup parent);

    public static class Item {

        private int mViewType;

        private Object mValue;
        private List<Item> mInvisibleChildren;
        public Item(int viewType, Object value) {
            mViewType = viewType;
            mValue = value;
        }

        public int getViewType() {
            return mViewType;
        }

        public Object getValue() {
            return mValue;
        }

        public List<Item> getInvisibleChildren() {
            return mInvisibleChildren;
        }

        public void setInvisibleChildren(List<Item> invisibleChildren) {
            mInvisibleChildren = invisibleChildren;
        }

        public void addInvisibleChild(Item item) {
            if (mInvisibleChildren == null) {
                mInvisibleChildren = new ArrayList<>();
            }

            mInvisibleChildren.add(item);
        }

    }

    abstract static class ParentViewHolder<T> extends ViewHolder<T> {

        private ImageView mViewToggle;

        @DrawableRes
        private int mParentBckg;
        @DrawableRes
        private int mParentBckgSelected;
        public ParentViewHolder(View itemView, @IdRes int toggleId) {
            super(itemView);

            if (toggleId != -1) {
                mViewToggle = itemView.findViewById(toggleId);
            }
        }

        public ParentViewHolder(View itemView, @IdRes int toggleId, int parentBckg, int parentBckgSelected) {
            this(itemView, toggleId);
            mParentBckg = parentBckg;
            mParentBckgSelected = parentBckgSelected;
        }

        private void setToggle(Item item) {
            if (mViewToggle != null) {
                if (item.getInvisibleChildren() == null) {
                    mViewToggle.setImageResource(COLLAPSE_TOGGLE);
                } else {
                    mViewToggle.setImageResource(EXPAND_TOGGLE);
                }
            }
        }

        public int getBackground() {
            return mParentBckg;
        }

        public int getBackgroundSelected() {
            return mParentBckgSelected;
        }

    }
    abstract static class ChildViewHolder<T> extends ViewHolder<T> {

        @DrawableRes
        private int mChildBckg;
        @DrawableRes
        private int mChildBckgSelected;
        public ChildViewHolder(View itemView) {
            super(itemView);
        }

        public ChildViewHolder(View itemView, int childBckg, int childBckgSelected) {
            super(itemView);
            mChildBckg = childBckg;
            mChildBckgSelected = childBckgSelected;
            itemView.setBackgroundResource(mChildBckg);
        }

        public int getBackground() {
            return mChildBckg;
        }

        public int getBackgroundSelected() {
            return mChildBckgSelected;
        }

    }
    abstract static class ViewHolder<T> extends RecyclerView.ViewHolder implements ViewHolderData<T> {

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }
    private interface ViewHolderData<T> {

        void setData(T data);
        @DrawableRes
        int getBackground();

        @DrawableRes
        int getBackgroundSelected();

    }

    protected int getPx(Context context, int valueInDp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                valueInDp,
                r.getDisplayMetrics()
        );
    }
}
