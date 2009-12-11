class EditCommType < ActiveRecord::Migration
 
def self.up

remove_column :commType
add_column :commType :string, :default => 'Call'

end

 

def self.down
  
remove_column :commType
end


end
