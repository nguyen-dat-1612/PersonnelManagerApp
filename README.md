# Cấu trúc thư mục chính

```plaintext
app/
├── src/
│   ├── main/
│   │   ├── java/com/managerapp/personnelmanagerapp/
│   │   │   ├── data/              # Chứa data layer (API, Database, Repository)
│   │   │   │   ├── remote/        # Chứa các service gọi API
│   │   │   │   ├── local/         # Chứa dữ liệu cục bộ
│   │   │   │   ├── repository/    # Chứa repository (cầu nối giữa Data Layer và ViewModel)
│   │   │   ├── di/                # Dependency Injection (Hilt)
│   │   │   ├── domain/            # Chứa business logic (Use Cases)
│   │   │   │   ├── model/         # Chứa các data class, entity
│   │   │   │   ├── usecase/       # Chứa Use Case
│   │   │   ├── ui/                # UI Layer (View, ViewModel)
│   │   │   │   ├── adapters/      # Adapter cho RecyclerView
│   │   │   │   ├── activities/    # Chứa các Activity
│   │   │   │   ├── fragments/     # Chứa các Fragment
│   │   │   │   ├── viewmodel/     # ViewModel cho mỗi màn hình
│   |   │   ├── utils/         # Chứa helper class, extension function
│   │   ├── App.java               # Application class
│   ├── res/
│   │   ├── layout/                # XML layout
│   │   ├── values/                # Strings, styles, themes
│   ├── test/                      # Unit Test
│   ├── androidTest/               # UI Test
```
# Giải thích các thành phần chính

## Data Layer (`data/`)
- **remote** chứa api gọi cơ sở dữ liệu từ xa
- **local** chứa cơ sở dữ liệu cục bộ
- **`repository/`**: Đóng vai trò trung gian giữa API/DB và ViewModel.

## Domain Layer (`domain/`)
- Chứa **Model** 
- Chứa **Use Case** để xử lý logic nghiệp vụ.

## UI Layer (`ui/`)
- **`adapters/`**: Chứa các adapter cho RecyclerView.
- **`activities/`**: Chứa các màn hình chính (`Activity`).
- **`fragments/`**: Chứa các màn hình con (`Fragment`).
- **`viewmodel/`**: Chứa ViewModel cho từng màn hình.

## Dependency Injection (`di/`)
- Chứa các module **Hilt** để quản lý Dependency Injection.

# Design Pattern
- **MVVM**: Phân tách **UI (View)**, **logic xử lý (ViewModel)**, và **dữ liệu (Model)**.
- **Repository Pattern**: Tách API/Database khỏi ViewModel.
- **Singleton Pattern**: Áp dụng cho Retrofit, Room Database.
- **Dependency Injection**: Dùng Hilt để inject Repository, ViewModel.
