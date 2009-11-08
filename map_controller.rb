require 'ym4r_gm'

class MapController < ApplicationController
  def index
    # Create a new map object, also defining the div ("map") 
    # where the map will be rendered in the view
    @map = GMap.new("map")
    # Use the larger pan/zoom control but disable the map type
    # selector
    @map.control_init(:large_map => true,:map_type => false)
    # Center the map on specific coordinates and focus in fairly
    # closely
    @map.center_zoom_init([41.023849,-80.682053], 10)

    @locations = Location.all
    print @locations
    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @locations }
    end
  end 
end
