CroPicker
=========

Demo
----
* Coming soon.

Compatibility
-------------
* Supports Android ICS 4.0.3+ (API 15)

Download
--------
```groovy
dependencies {
  compile 'xyz.dev_juyoung:cropicker:1.0.0'
}
```

Required Permission
-------------------
* AndroidManifest.xml
    ```xml
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    ```
* Also, if your application is targetSDK version 23+,
  At runtime, you have to check permission and request permission from the user.
* Is there any difficulty in implementing it yourself?
  visit [android-arsenal][arsenal] for find libraries.
* My sample project uses [TedPermission][ted-permission].
  **TedPermission** is a **very simple** and **smart** permission check library, so I recommend it.

Usage
-----
* The CroPicker configuration is created using the builder pattern.
    ```java
    // Optional
    // Customization Options
    CroPicker.Options options = new CroPicker.Options();
    options.setLimitedCount(5);
    options.setMessageViewType(CroPicker.MESSAGE_VIEW_TYPE_SNACKBAR);

    CroPicker
        .init(activity)
        .withOptions(options) //Optional
        .start();
    ```
* Override onActivityResult method and handle CroPicker result.
    ```java
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CroPicker.REQUEST_ALBUM) {
            ArrayList<Media> results = data.getParcelableArrayListExtra(CroPicker.EXTRA_RESULT_IMAGES);
        }
    }

    ```

Customization
-------------
CroPicker builder class has method `withOptions(CroPicker.Options options)`.
When used with the `withOptions ()` method, you can customize some of the desired configurations.

**Methods**

* **setToolbarColor(@ColorInt)**
  * **default** `#3F51B5`
  * `setToolbarColor(ContextCompat.getColor(context, R.color.xxx))`
* **setStatusBarColor(@ColorInt)**
  * **default** `#303F9F`
  * `setToolbarColor(ContextCompat.getColor(context, R.color.xxx))`
* **setToolbarWidgetColor(@ColorInt)**
  * **default** `#FFFFFF`
  * `setToolbarWidgetColor(ContextCompat.getColor(context, R.color.xxx)`
* **setToolbarTitle(String)**
  * **default** `en - Album / kr - 앨범`
  * `setToolbarTitle("Example") or setToolbarTitle(getString(R.string.xxx))`
* **setToolbarBackArrowDrawable(@DrawableRes)**
  * **default** `R.drawable.abc_ic_ab_back_material`
  * `setToolbarBackArrowDrawable(R.drawable.xxx)`
* **setToolbarDoneDrawable(@DrawableRes)**
  * **default** [material.io/icons/#ic_done][done]
  * **recommend size** 24dp
  * `setToolbarDoneDrawable(R.drawable.xxx)`
* **setAlbumGridSpanCount(Int)**
  * **default** 2
  * `setAlbumGridSpanCount(3)`
* **setMediaGridSpanCount(Int)**
  * **default** 3
  * `setMediaGridSpanCount(4)`
* **setGridSpacing(@DimenRes)**
  * **default** 4dp
  * `setGridSpacing(R.dimen.xxx)`
* **setOverlayViewBackgroundColor(@ColorInt)**
  * **default** `#66000000`
  * `setOverlayViewBackgroundColor(ContextCompat.getColor(this, R.color.xxx))`
* **setOverlayViewStrokeColor(@ColorInt)**
  * **default** `#3F51B5`
  * `setOverlayViewStrokeColor(ContextCompat.getColor(this, R.color.xxx))`
* **setOverlayViewStrokeWidth(@DimenRes)**
  * **default** 4dp
  * `setOverlayViewStrokeWidth(R.dimen.xxx)`
* **setIndexViewType(Int)**
  * **default** `CroPicker.INDEX_VIEW_TYPE_TEXT`
  * `setIndexViewType(CroPicker.INDEX_VIEW_TYPE_TEXT) or setIndexViewType(CroPicker.INDEX_VIEW_TYPE_ICON)`
* **setIndexViewTextSize(Int)**
  * **default** 24sp
  * **UNIT** `TypedValue.COMPLEX_UNIT_SP`
  * `setIndexViewTextSize(30)`
* **setIndexViewTextColor(@ColorInt)**
  * **default** `#FFFFFF`
  * `setIndexViewTextColor(ContextCompat.getColor(this, R.color.xxx))`
* **setIndexViewIconDrawable(@DrawableRes)**
  * **default** [material.io/icons/#ic_check_circle][check]
  * **recommend size** 48dp
  * `setIndexViewIconDrawable(R.drawable.xxx)`
* **setNotSelectedMessage(String)**
  * **default** `en - Did you image choose? / kr - 선택된 이미지가 없어요...`
  * `setNotSelectedMessage("Not Selected!!") or setNotSelectedMessage(getString(R.string.xxx))`
* **setLimitedCount(Int)**
  * **default** `Integer.MAX_VALUE`
  * `setLimitedCount(10)`
* **setLimitedExceedMessage(String)**
  * **default** `en - You can not select any more. / kr - 최대 n장 까지만 선택 가능합니다.`
  * `setLimitedExceedMessage("BlahBlah") or setLimitedExceedMessage(getString(R.string.xxx)`
* **setMessageViewType(Int)**
  * **default** `CroPicker.MESSAGE_VIEW_TYPE_TOAST`
  * `setMessageViewType(CroPicker.MESSAGE_VIEW_TYPE_TOAST) or setMessageViewType(CroPicker.MESSAGE_VIEW_TYPE_SNACKBAR)`

Thanks
------
* [uCrop][ucrop] - Image Cropping Library for Android / Inspired by uCrop's builder pattern.
* [ButterKnife][butterknife] - Bind Android views and callbacks to fields and methods.
* [Glide][glide] - An image loading and caching library for Android focused on smooth scrolling
* [TedPermission][ted-permission] - Easy check permission library for Android Marshmallow

License
-------

    Copyright 2017 Juyoung, Lee

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



 [done]: https://material.io/icons/#ic_done
 [check]: https://material.io/icons/#ic_check_circle
 [arsenal]: https://android-arsenal.com/tag/235?sort=created
 [ucrop]: https://github.com/Yalantis/uCrop
 [butterknife]: https://github.com/JakeWharton/butterknife
 [glide]: https://github.com/bumptech/glide
 [ted-permission]: https://github.com/ParkSangGwon/TedPermission