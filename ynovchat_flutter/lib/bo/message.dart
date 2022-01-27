import 'package:ynovchat_flutter/bo/user.dart';

class Message{
  String content;
  User author;
  String created_at;
  bool isImage = false;

  Message(this.content, this.author, this.created_at);

  Message.fromJson(Map<String, dynamic> json) :
    content = json["content"],
    author = User.fromJson(json["author"]),
    created_at = json["created_at"],
    isImage = json["isImage"];


  Map<String, dynamic> toJson() => {
    "content" : content,
    "author" : author.toJson(),
    "created_at" : created_at,
    "isImage" : isImage,
  };
}