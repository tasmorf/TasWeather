# TasWeather

## How to build
This is a regular android project, all you need to do is run it either in Android Studio, or via gradle,
 using the following command:

 ``` gradlew assembleDebug ```

## Testing
You will notice though, that the bulk of the logic is contained in a class called ```ForecastConverter``` which is unit tested. Since the server response has been converted into
an easy to use client bean the UI logic is minimal. Again, the bulk of the view setting logic is in ```DailyForecastLayout```. An Espresso test suite
with mock responses has been added as well.

## If I had more time...
There are tons of things I'd do:
- Implement a search field in the toolbar for getting the forecast for different cities
- Support landscape mode
- Have a tablet layout
- Have an actual chart overlaid on the timepicker, with the temperature and precipitation for that time
- Support different metric units