package com.managerapp.personnelmanagerapp.data.utils;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;

import io.reactivex.rxjava3.core.Single;

// <editor-fold desc="📌 NOTE CHO TEAM - RxResultHandler xử lý kết quả từ API dùng RxJava">
/*
📦 RxResultHandler là utility class giúp xử lý response trả về từ API khi dùng RxJava (Single<BaseResponse<T>>).

🎯 Mục đích:
- Trích xuất `data` từ `BaseResponse<T>` nếu code trả về là 200.
- Nếu response có lỗi (code khác 200 hoặc data null), ném ra Exception với message tương ứng.
- Nếu gặp lỗi parse JSON (ví dụ khi API trả sai định dạng), trả về lỗi có nội dung dễ hiểu hơn: `"Data format error"`.

🔄 Flow tổng quát:
1. Nhận `Single<BaseResponse<T>>`
2. Nếu `response.code == 200 && response.data != null` → `Single.just(data)`
3. Ngược lại → `Single.error(Exception(message))`
4. Nếu lỗi `JsonSyntaxException` → wrap lại lỗi với message `"Data format error"`
5. Các lỗi còn lại được propagate nguyên trạng.

📌 Gợi ý sử dụng:
Trong Repository:
```java
return api.getSomething()
          .compose(RxResultHandler::handle);
*/

public class RxResultHandler {
    public static <T> Single<T> handle(Single<BaseResponse<T>> source) {
        return source.flatMap(response -> {
            if (response.getCode() == 200 && response.getData() != null) {
                Log.d("RxResultHandler", "Success: " + response.getData());
                return Single.just(response.getData());
            } else {
                Log.e("RxResultHandler", "API failed: " + response.getMessage());
                return Single.error(new Exception(response.getMessage()));
            }
        }).onErrorResumeNext(throwable -> {
            if (throwable instanceof JsonSyntaxException) {
                Log.e("RxResultHandler", "JSON parse error", throwable);
                return Single.error(new Exception("Data format error"));
            }
            Log.e("RxResultHandler", "API error", throwable);
            return Single.error(throwable);
        });
    }
}
