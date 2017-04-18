CroPicker
=========
CroPicker는 앨범 및 이미지 선택을 위한 안드로이드용 라이브러리입니다.  
앨범의 목록을 가져오고 여러장의 이미지를 선택할 수 있는 기능을 지원합니다.

Demo
----
* 준비중입니다.

Compatibility
-------------
* Android ICS 4.0.3(API 15) 이상 지원합니다.

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
* 만약 여러분의 어플리케이션의 targetSDK 버전이 23이상인 경우,  
  여러분은 런타임 시점에서 권한을 확인하고 사용자에게 권한을 요청해야합니다.
* 스스로 구현함에 있어 어려움이 있으신가요?  
  그렇다면 [Arsenal][android-arsenal]에서 라이브러리르 찾아보세요.
* 저의 샘플 프로젝트에서는 [TedPermission][ted-permission]을 사용하고 있습니다.  
  **TedPermission**은 매우 간단하고 스마트한 권한확인 라이브러리 입니다. 저는 이 라이브러리를 추천합니다.

Usage
-----
* CroPicker의 구성은 빌더 패턴을 이용하여 작성됩니다.
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
* onActivityResult 메서드를 재정의하고 CroPicker 결과를 처리합니다.
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
CroPicker 빌더 클래스에는`withOptions (CroPicker.Options options)`메소드가 있습니다.  
'withOptions()'메소드와 함께 사용하면, 몇가지의 원하는 설정을 사용자 정의 할 수 있습니다.

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
* [uCrop][ucrop] - Android용 이미지 크롭 라이브러리입니다. uCrop의 빌더패턴에서 영감을 얻었습니다.
* [ButterKnife][butterknife] - Android용 바인딩 라이브러리입니다.
* [Glide][glide] - Android용 이미지로드 및 캐싱 라이브러리입니다.
* [TedPermission][ted-permission] - Android Marshmallow에 대한 권한확인 라이브러리입니다.

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
 [android-arsenal]: https://android-arsenal.com/tag/235?sort=created
 [ucrop]: https://github.com/Yalantis/uCrop
 [butterknife]: https://github.com/JakeWharton/butterknife
 [glide]: https://github.com/bumptech/glide
 [ted-permission]: https://github.com/ParkSangGwon/TedPermission
 [readme-kr]: https://github.com/dev-juyoung/CroPicker/blob/master/README-KR.md
