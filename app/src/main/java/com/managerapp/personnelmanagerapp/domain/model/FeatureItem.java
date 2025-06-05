package com.managerapp.personnelmanagerapp.domain.model;

import java.util.List;

public class FeatureItem {
    private final int viewId;
    private final int navDestination;
    private final List<Role> visibleFor;

    public FeatureItem(int viewId, int navDestination, List<Role> visibleFor) {
        this.viewId = viewId;
        this.navDestination = navDestination;
        this.visibleFor = visibleFor;
    }

    public int getViewId() {
        return viewId;
    }

    public int getNavDestination() {
        return navDestination;
    }

    public boolean isVisibleFor(Role role) {
        return visibleFor.contains(role);
    }
}
