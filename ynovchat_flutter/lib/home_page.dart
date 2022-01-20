import 'dart:async';
import 'dart:convert';
import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:http/http.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:intl/intl.dart';
import 'package:timeago/timeago.dart';

import 'package:ynovchat_flutter/message.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  late StreamController<List<Message>> _streamControllerListMsgs;
  late Stream<List<Message>> _streamMsgs;

  @override
  void initState() {
    super.initState();
    //Intl
    initializeDateFormatting('fr_FR', null);
    //TimeAgo
    setLocaleMessages("fr_short", FrShortMessages());
    _streamControllerListMsgs = StreamController<List<Message>>();
    _streamMsgs = _streamControllerListMsgs.stream;
    fetchMessages();
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Ynov Chat"),
      ),
      body: _buildList()
    );
  }

  StreamBuilder<List<Message>> _buildList() {
    return StreamBuilder<List<Message>>(
      stream: _streamMsgs,
      builder: (context, snapshot) {
        if(snapshot.hasError) {
          return const Center(child:  Icon(Icons.error));
        }
        if(!snapshot.hasData) {
          return const Center(child: const CircularProgressIndicator());
        } else {
          return ListView.separated(
            itemCount: snapshot.data!.length,
            separatorBuilder:
              (BuildContext context, int index) => const Divider(/*thickness: 1.5,*/),
            itemBuilder: (context, index) =>
              ListTile(
                leading: Image.network('https://fujifilm-x.com/wp-content/uploads/2019/08/x-t3_sample-images02.jpg'),
                title: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(snapshot.data![index].author.username),
                    Text(formatDateString(snapshot.data![index].created_at),
                      style: const TextStyle(fontStyle: FontStyle.italic),
                    ),
                  ]
                ,),
                subtitle: Text(snapshot.data![index].content),
            )
          );
        }
      }
    );
  }

  String formatDateString(String isoDate){
    DateFormat df = DateFormat("yyyy-MM-ddTHH:mm:ss.SSSZ","fr_FR");
    DateTime dateTime = df.parse(isoDate);
    return format(dateTime,locale: 'fr_short');
  }

  void fetchMessages(){
    Future<Response> resMsgs = get(
      Uri.parse("https://flutter-learning.mooo.com/messages"),
      headers: {"Authorization":"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaWF0IjoxNjQyNjcxMzMyLCJleHAiOjE2NDUyNjMzMzJ9.46AmdmoaNWPaYdDoR-4YImCSBNROendkxWD5_oz39Nc"}
    );
    resMsgs.then(
      (value) {
        if(value.statusCode==200){
          String jsonBody = value.body;
          List<Message> lsMsgs = List.empty(growable: true);
          for(Map<String,dynamic> msg in jsonDecode(jsonBody)){
            lsMsgs.add(Message.fromJson(msg));
          }
          _streamControllerListMsgs.sink.add(lsMsgs);
        }
      },
      onError: (_, err) => log("Erreur lors du download des msgs:" + err.toString())
    );
  }

  @override
  void dispose() {
    _streamControllerListMsgs.close();
  }
}

