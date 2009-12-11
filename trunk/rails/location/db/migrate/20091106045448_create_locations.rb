class CreateLocations < ActiveRecord::Migration
  def self.up
    create_table :locations do |t|
      t.string :username
      t.integer :phonenumber
      t.float :latitude
      t.float :longitude
      t.time :time
      t.string :commType

      t.timestamps
    end
  end

  def self.down
    drop_table :locations
  end
end
