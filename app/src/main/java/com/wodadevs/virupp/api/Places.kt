package com.wodadevs.virupp.api

class Places {
        var html_attributions: Array<String>?=null
        var status:String?=null
        var next_age_token:String?=null
        var results:Array<Results>?=null
}

class Results{
        var name: String?=null
        var icon: String?=null
        var geometry:Geometry?=null
        var photos:Array<Photo>?=null
        var id:String?=null
        var place_id:String?=null
        var price_level:Int=0
        var rating:Double=0.0
        var reference:String?=null
        var scope:String?=null
        var types:Array<String>?=null
        var vicinity: String?=null
        var opening_hours:OpeningHours?=null
}

class Geometry{
        var viewport:Viewport?=null
        var location:Location?=null
}

class Photo{
        var height:Int=0
        var html_attributions:Array<String>?=null
        var photo_reference:String?=null
}

class OpeningHours{
        var open_now:Boolean=false
        var weekday_text:Array<String>?=null
}

class Viewport{
        var notheast:Northeast?=null
        var southwest:Southwest?=null
}

class Northeast{
        var lat:Double=0.0
        var lng:Double=0.0
}

class Southwest{
        var lat:Double=0.0
        var lng:Double=0.0
}

class Location {
        var lat:Double=0.0
        var lng:Double=0.0
}

