require 'ym4r_gm'

class MapController < ApplicationController


  def index
    # Create a new map object, also defining the div ("map") 
    # where the map will be rendered in the view
    @map = GMap.new("map")
    # Use the larger pan/zoom control but disable the map type
    # selector
    @map.control_init(:large_map => true,:map_type => true)
    # Center the map on specific coordinates and focus in fairly
    # closely
    #@map.center_zoom_init([41.023849,-80.682053], 10)

    @locations = Location.find(:all, :conditions => [ "username=?",session[:user_id]])
    init_location = Location.find(:first, :conditions => [ "username=?",session[:user_id]])
    #@map.addControl(new GMapTypeControl());
    
    if init_location
      @map.center_zoom_init([init_location.latitude,init_location.longitude], 10)  
    else
       @map.center_zoom_init([40.468176,-74.445227], 10)  
    end
    print @locations
    
    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @locations }
    end
    end
 end


