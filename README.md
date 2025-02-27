1. Cấu trúc thư mục chính
app/
 ├── src/
 │   ├── main/
 │   │   ├── java/com/managerapp/personnelmanagerapp/
 │   │   │   ├── data/                # Chứa data layer (API, Database, Repository)
 │   │   │   │   ├── api/             # Chứa các service gọi API
 │   │   │   │   ├── database/        # Chứa Room Database, Dao
 │   │   │   │   ├── model/           # Chứa các data class, entity
 │   │   │   │   ├── repository/      # Chứa repository (cầu nối giữa Data Layer và ViewModel)
 │   │   │   │   ├── pref/            # Chứa SharedPreferences
 │   │   │   │   ├── datasource/      # Chứa RemoteDataSource, LocalDataSource
 │   │   │   ├── di/                  # Dependency Injection (Dagger)
 │   │   │   ├── domain/              # Chứa business logic (Use Cases)
 │   │   │   ├── ui/                  # UI Layer (View, ViewModel)
 │   │   │   │   ├── adapters/        # Adapter cho RecyclerView
 │   │   │   │   ├── activities/      # Chứa các Activity
 │   │   │   │   ├── fragments/       # Chứa các Fragment
 │   │   │   │   ├── viewmodel/       # ViewModel cho mỗi màn hình
 │   │   │   ├── utils/               # Chứa helper class, extension function
 │   │   │   ├── App.java             # Application class
 │   │   │   ├── MainActivity.java    # MainActivity
 │   │   ├── res/
 │   │   │   ├── layout/              # XML layout
 │   │   │   ├── values/              # Strings, styles, themes
 │   ├── test/                        # Unit Test
 │   ├── androidTest/                 # UI Test
2. Giải thích các thành phần chính
Data Layer (data/)
- api/: Chứa các interface gọi API bằng Retrofit.
- database/: Chứa các entity và DAO của Room Database.
- model/: Chứa các class dữ liệu.
- repository/: Đóng vai trò trung gian giữa API/DB và ViewModel.
- pref/: Lưu dữ liệu nhẹ bằng SharedPreferences.
- datasource/: Chứa RemoteDataSource và LocalDataSource.
Domain Layer (domain/)
- Chứa Use Case để xử lý logic nghiệp vụ.
UI Layer (ui/)
- adapters/: Chứa các adapter cho RecyclerView.
- activities/: Chứa các màn hình chính (Activity).
- fragments/: Chứa các màn hình con (Fragment).
- viewmodel/: Chứa ViewModel cho từng màn hình.
Dependency Injection (di/)
- Chứa các module Dagger để quản lý Dependency Injection.
3. Design Pattern
- MVVM: Phân tách UI (View), logic xử lý (ViewModel), và dữ liệu (Model).
- Repository Pattern: Tách API/Database khỏi ViewModel.
- Singleton Pattern: Áp dụng cho Retrofit, Room Database.
- Dependency Injection: Dùng Dagger để inject Repository, ViewModel.
