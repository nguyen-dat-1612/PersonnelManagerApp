package com.managerapp.personnelmanagerapp.domain.model;

import java.util.List;

public class FeatureItem {
    private final int viewId;
    private final int navDestination;
    private final List<RoleEnum> visibleFor;

    public FeatureItem(int viewId, int navDestination, List<RoleEnum> visibleFor) {
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

    public boolean isVisibleFor(RoleEnum role) {
        return visibleFor.contains(role);
    }
}
