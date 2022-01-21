import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:ynovchat_flutter/page/home_page.dart';
import 'package:ynovchat_flutter/page/map_page.dart';
import 'package:ynovchat_flutter/page/register_page.dart';
import 'package:ynovchat_flutter/routes.dart';
import 'package:latlng/latlng.dart';
import 'page/login_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.green,
      ),
      darkTheme: ThemeData.dark(),


      onGenerateRoute: (settings){
        if(settings.name == ROUTE_MAP_PAGE){
          return MaterialPageRoute(builder:(context) =>
            MapPage(settings.arguments as LatLng));
        }
      },
      initialRoute: ROUTE_HOME_PAGE,
      routes: <String, WidgetBuilder>{
        ROUTE_LOGIN:(context) => LoginPage(),
        ROUTE_REGISTER:(context) => RegisterPage(),
        ROUTE_HOME_PAGE:(context) => HomePage(),
        //ROUTE_MAP_PAGE:(BuildContext context) => MapPage(),
      },
    );
  }
}
