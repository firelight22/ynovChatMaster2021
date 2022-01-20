import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:developer' as developer;

class LoginPage extends StatelessWidget {
  late TextEditingController tecId, tecPwd;

  LoginPage({Key? key}) : super(key: key){
    tecId = TextEditingController();
    tecPwd = TextEditingController();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Connexion"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Center(
          child: Column(
            children: [
              const Spacer(),
              SizedBox(
                width: 200.0,
                child: TextFormField(
                  controller: tecId,
                  decoration: const InputDecoration(
                    hintText: "Identifiant"
                  ),
                ),
              ),
              SizedBox(
                width: 200.0,
                child: TextFormField(
                  controller: tecPwd,
                  decoration: const InputDecoration(
                    hintText: "Mot de passe",
                  ),
                  obscureText: true,
                ),
              ),
              const Spacer(),
              ElevatedButton(
                onPressed: () => _login(context),
                child: Text("Se connecter".toUpperCase(),)
              ),
              OutlinedButton(
                onPressed: ()=> Navigator.of(context).pushNamed('/register'),
                child: Text("S'inscrire".toUpperCase())
              )
            ],
          ),
        ),
      ),
    );
  }

  void _login(BuildContext context){
    String id = tecId.text;
    String password = tecPwd.text;
    Future<http.Response> res = http.post(
      Uri.parse("https://flutter-learning.mooo.com/auth/local"),
      body: {
        "identifier": id,
        "password": password
      }
    );
    res.then((value) {
      if(value.statusCode == 200){
        //YOOPI
        Map<String,dynamic> bodyJson = jsonDecode(value.body);
        developer.log(bodyJson["jwt"]);
      }
    }, onError: (obj){
      developer.log("erreur lors de la connexion " + obj.toString());
    });
    tecId.text = "";
    tecPwd.text = "";

  }
}


