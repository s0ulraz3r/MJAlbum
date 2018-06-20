# MJAlbum

* This is a responsive music explorer app to fetch media information using **iTunes music API**
*	**LruCache** module is used to cache the Bitmap images, this improved the performance by reducing the request to the server
*	The HTTP response is handled by utilizing **HttpUrlConnection, JsonArray & AsyncTask** which **reduces the workload** on the **main UI thread** 

##Note:
> This application is built using core Android components. No third party libraries are used.

### Technology Stack
* **Java, HttpUrlConnection, LruCache, iTunes API, Bitmap, JsonArray**

## About the app

* The data is organised and displayed using **ListView** and **Constraint Layout**
* Users can click on the song which ever they like to view more details.
* The app also provides the button to listen to the preview music.
