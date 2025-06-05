package com.managerapp.personnelmanagerapp.manager;

//import com.managerapp.personnelmanagerapp.domain.model.UserRole;
//
//import java.util.Set;

//public class PermissionManager {
//    private static PermissionManager instance;
//    private Set<String> userPermissions; // Danh sách quyền của user
//    private UserRole userRole; // Vai trò của user
//
//    // Singleton pattern để đảm bảo chỉ có 1 instance
//    public static PermissionManager getInstance() {
//        if (instance == null) {
//            instance = new PermissionManager();
//        }
//        return instance;
//    }
//
//    // Kiểm tra user có quyền cụ thể không
//    public boolean hasPermission(String permission) {
//        return userPermissions.contains(permission);
//    }
//
//    // Kiểm tra user có tất cả quyền trong danh sách không
//    public boolean hasPermissions(List<String> permissions) {
//        return userPermissions.containsAll(permissions);
//    }
//
//    // Kiểm tra có thể truy cập màn hình không
//    public boolean canAccessDestination(int destinationId) {
//        String requiredPermission = getRequiredPermissionForDestination(destinationId);
//        return requiredPermission == null || hasPermission(requiredPermission);
//    }
//
//    // Map màn hình với quyền cần thiết
//    private String getRequiredPermissionForDestination(int destinationId) {
//        Map<Integer, String> permissionMap = new HashMap<>();
//        permissionMap.put(R.id.admin_fragment, "ADMIN");
//        permissionMap.put(R.id.user_management_fragment, "USER_MANAGEMENT");
//        permissionMap.put(R.id.reports_fragment, "VIEW_REPORTS");
//
//        return permissionMap.get(destinationId);
//    }
//}