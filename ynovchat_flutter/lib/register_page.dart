import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'dart:developer' as developer;

import 'package:ynovchat_flutter/routes.dart';

class RegisterPage extends StatelessWidget {
  late TextEditingController tecEmail,tecUsername,tecPassword;

  RegisterPage({Key? key}) : super(key: key){
    tecEmail = TextEditingController();
    tecUsername = TextEditingController();
    tecPassword = TextEditingController();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Inscription"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Center(
          child: Column(
            children: [
              Spacer(),
              _buildEmailField(),
              _buildUsernameField(),
              _buildPasswordField(),
              Spacer(),
              _buildRegisterButton(context),
              _buildLoginButton(context)
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildEmailField() => SizedBox(
    width: 200.0,
    child: TextFormField(
      controller: tecEmail,
      keyboardType: TextInputType.emailAddress,
      textInputAction: TextInputAction.next,
      decoration: const InputDecoration(hintText: "Email"),
    ),
  );

  Widget _buildUsernameField() => SizedBox(
    width: 200.0,
    child: TextFormField(
      controller: tecUsername,
      keyboardType: TextInputType.text,
      textInputAction: TextInputAction.next,
      decoration: const InputDecoration(hintText: "Username"),
    ),
  );

  Widget _buildPasswordField() => SizedBox(
    width: 200.0,
    child: TextFormField(
      controller: tecPassword,
      obscureText: true,
      textInputAction: TextInputAction.send,
      decoration: const InputDecoration(hintText: "Mot de passe"),
    ),
  );

  Widget _buildRegisterButton(BuildContext context) => ElevatedButton(
    onPressed: () => register(context),
    child: Text("S'inscrire".toUpperCase())
  );

  Widget _buildLoginButton(BuildContext context) => OutlinedButton(
    onPressed: ()=> Navigator.of(context).pushReplacementNamed('/login'),
    child: Text("Se connecter".toUpperCase())
  );

  void register(BuildContext context) {
    String username = tecUsername.text;
    String email = tecEmail.text;
    String password = tecPassword.text;

    Future<http.Response> res = http.post(
      Uri.parse("https://flutter-learning.mooo.com/auth/local/register"),
      body: {
        "email": email,
        "username": username,
        "password": password
      }
    );
    res.then((value) async {
      if(value.statusCode == 200) {
        //YOOPI
        Map<String,dynamic> bodyJson = jsonDecode(value.body);
        String jwt = bodyJson["jwt"];
        developer.log(jwt);
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content:Text("Inscription réussie")));
        tecPassword.text = tecEmail.text = tecUsername.text = "";
        await FlutterSecureStorage().write(key: "jwt", value: jwt).then(
            (value) => Navigator.of(context).pushReplacementNamed(ROUTE_HOME_PAGE),
          onError: (_, error) => developer.log("Erreur Sauvegarde token : "
            + error.toString())
        );
      }
    }, onError: (obj){
      developer.log("erreur lors de l'inscription " + obj.toString());
    });
  }

}
