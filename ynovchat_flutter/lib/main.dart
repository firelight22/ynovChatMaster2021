import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:ynovchat_flutter/home_page.dart';
import 'package:ynovchat_flutter/register_page.dart';
import 'package:ynovchat_flutter/routes.dart';

import 'login_page.dart';

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
      //onGenerateRoute: (settings){
      //  if(settings.name == '/register'){
      //    return MaterialPageRoute(builder:(BuildContext context) =>
      //      RegisterPage(settings.arguments as String));
      //  }
      //},
      initialRoute: ROUTE_HOME_PAGE,
      routes: <String, WidgetBuilder>{
        ROUTE_LOGIN:(BuildContext context) => LoginPage(),
        ROUTE_REGISTER:(BuildContext context) => RegisterPage(),
        ROUTE_HOME_PAGE:(BuildContext context) => HomePage(),
      },
    );
  }
}
