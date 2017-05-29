# TasWeather
4 Hours is not enough!!!!

Ok, seriously now...

## How to build
This is a regular android project, all you need to do is run it either in Android Studio, or via gradle,
 using the following command:

 ``` gradlew assembleDebug ````

## Testing
See my comment above for the 4 hours. I didn't have time to add either unit or UI tests. You will notice though, that the bulk of the logic
is contained in a class called ```ForecastConverter```. This would be very simple to unit test. Since the server response has been converted into
an easy to use client bean the UI logic is minimal. Again, the bulk of the view setting logic is in ```DailyForecastLayout```. An Espresso test
with mock responses, and different devices times should be pretty simple to right.

## If I had more time...
There are tons of things I'd do:
- Add unit tests
- Add UI tests
- Implement a search field in the toolbar for getting the forecast for different cities
- Support landscape mode
- Have a tablet layout
- Implement caching (I'd probably override the http cache headers of the response for that)
- Have an actual chart overlaid on the timepicker, with the temperature and precipitation for that time
- Support different metric units