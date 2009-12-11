class EditCommType < ActiveRecord::Migration
 
def self.up

remove_column :locations,  :commType
add_column :locations, :commType, :string, :default => 'Call'

end

 

def self.down
  
remove_column :locations, :commType
end


end
