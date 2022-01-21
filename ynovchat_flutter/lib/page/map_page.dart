import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:map/map.dart';
import 'package:latlng/latlng.dart';

class MapPage extends StatefulWidget {
  final LatLng location;
  const MapPage(this.location,{Key? key}) : super(key: key);

  @override
  _MapPageState createState() => _MapPageState();
}

class _MapPageState extends State<MapPage> {
  late MapController controller;
  @override
  void initState() {
    super.initState();
    controller = MapController(location: widget.location);
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Map(
        controller: controller,
        builder: (context, x, y, z) {
          final url ="https://tile.openstreetmap.org/$z/$x/$y.png";
          return CachedNetworkImage(
            imageUrl: url,
            fit: BoxFit.cover,
          );
        },
      ),
    );
  }
}
