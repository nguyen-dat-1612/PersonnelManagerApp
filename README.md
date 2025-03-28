# Personnel Manager App - Android Java

Dự án quản lý nhân sự sử dụng **Android Java + XML**, tuân thủ Clean Architecture và MVVM.

## 📁 Cấu Trúc Dự Án
```plaintext
app/
├── src/
│   ├── main/
│   │   ├── java/com/managerapp/personnelmanagerapp/
│   │   │   ├── data/
│   │   │   │   ├── di/            # Hilt Modules (Dependency Injection)
│   │   │   │   ├── remote/        # Retrofit + API Service, DTOs
│   │   │   │   ├── local/         # Room Database, SharedPreferences
│   │   │   │   ├── repository/    # Repository pattern
│   │   │   ├── domain/            # Pure Java (Không phụ thuộc Android)
│   │   │   │   ├── model/         # Entities/POJOs
│   │   │   │   ├── usecase/       # Business Logic (Clean Architecture)
│   │   │   ├── ui/                # UI Layer (MVVM)
│   │   │   │   ├── activities/    # Activity classes
│   │   │   │   ├── fragments/     # Fragment classes
│   │   │   │   ├── viewmodel/     # ViewModel + LiveData
│   │   │   │   ├── state/         # Sealed Interface/Class cho UI State
│   │   │   │   ├── adapters/      # RecyclerView.Adapter
│   │   │   ├── utils/             # Helper classes, Extensions
│   │   │   ├── service/           # Background Services
│   │   ├── res/
│   │   │   ├── layout/            # XML layouts (activity_*, fragment_*)
│   │   │   ├── values/            # strings.xml, colors.xml, styles.xml
│   │   │   ├── drawable/          # Icons, selectors, shapes
│   │   │   ├── navigation/        # NavGraph (Navigation Component)
│   ├── test/                      # JUnit tests (Domain/Data Layer)
│   ├── androidTest/               # Espresso tests (UI Layer)
```
## 🛠 Công Nghệ Sử Dụng

### **1. Kiến Trúc**
- **Clean Architecture** (Data - Domain - UI Layers)
- **MVVM** với ViewModel + LiveData
- **Sealed Interface** cho UI State Management

### **2. Thư Viện Chính**
| Loại               | Thư Viện                  | Mục Đích                     |
|--------------------|--------------------------|-----------------------------|
| Dependency Injection | Hilt                    | Quản lý dependencies        |
| Networking          | Retrofit + GSON         | Gọi API                     |
| Database            | Room                    | Local caching               |
| Navigation          | Navigation Component    | Điều hướng giữa màn hình     |
| Testing             | JUnit, Espresso         | Unit test & UI test         |

### **3. UI Components**
- **XML Layouts**: Activity/Fragment layouts
- **RecyclerView**: Hiển thị danh sách nhân viên
- **DataBinding**: Binding dữ liệu vào View 

