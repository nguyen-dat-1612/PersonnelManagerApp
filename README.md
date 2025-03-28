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
## 📚 Danh Mục Thư Viện

### **1. Core Android**
| Thư Viện | Phiên Bản | Mục Đích |
|----------|----------|----------|
| `androidx.appcompat` | 1.6.1 | Hỗ trợ backward compatibility |
| `androidx.core:core-ktx` | 1.12.0 | Extension functions cho Kotlin |
| `androidx.lifecycle` | 2.6.2 | ViewModel & LiveData |
| `androidx.constraintlayout` | 2.1.4 | Layout nâng cao |
| `com.google.android.material` | 1.11.0 | Material Design Components |

### **2. Networking**
| Thư Viện | Phiên Bản | Mục Đích |
|----------|----------|----------|
| `Retrofit` | 2.9.0 | REST API Client |
| `Gson Converter` | 2.9.0 | Chuyển đổi JSON ↔ Object |
| `OkHttp Logging` | 4.11.0 | Debug API requests |

### **3. Database & Local Storage**
| Thư Viện | Phiên Bản | Mục Đích |
|----------|----------|----------|
| `Room` | 2.6.1 | Local SQLite Database |
| `DataStore` | 1.0.0 | Thay thế SharedPreferences |
| `Security Crypto` | 1.1.0-alpha03 | Mã hóa dữ liệu nhạy cảm |

### **4. Dependency Injection**
| Thư Viện | Phiên Bản | Mục Đích |
|----------|----------|----------|
| `Hilt` | 2.51.1 | DI cho Android |

### **5. UI & Animation**
| Thư Viện | Phiên Bản | Mục Đích |
|----------|----------|----------|
| `Navigation Component` | 2.7.7 | Điều hướng giữa màn hình |
| `Glide` | 4.16.0 | Load và cache ảnh |
| `Lottie` | 6.1.0 | Animation chất lượng cao |
| `SmoothBottomBar` | 1.7.9 | Bottom Navigation đẹp |
| `SwipeRefreshLayout` | 1.1.0 | Pull-to-refresh |

### **6. Reactive Programming**
| Thư Viện | Phiên Bản | Mục Đích |
|----------|----------|----------|
| `RxJava 3` | 3.1.6 | Reactive Extensions |
| `RxAndroid` | 3.0.2 | RxJava trên UI Thread |
| `Retrofit RxJava Adapter` | 2.9.0 | Kết hợp Retrofit + RxJava |

### **7. Firebase**
| Thư Viện | Phiên Bản | Mục Đích |
|----------|----------|----------|
| `Firebase BOM` | 33.10.0 | Quản lý phiên bản tự động |
| `Firebase Messaging` | 23.0.0 | Push Notification |

### **8. Security & Authentication**
| Thư Viện | Phiên Bản | Mục Đích |
|----------|----------|----------|
| `Java JWT` | 3.19.2 | Giải mã token |
| `Biometric` | 1.1.0 | Xác thực vân tay/khuôn mặt |
### **3. UI Components**
- **XML Layouts**: Activity/Fragment layouts
- **RecyclerView**: Hiển thị danh sách
- **DataBinding**: Binding dữ liệu vào View 

