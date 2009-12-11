class UserController < ApplicationController


def authenticate
        #find records with username,password
        valid_user = User.authenticate(params[:userform][:userName], params[:userform][:password] )
  
       #if statement checks whether valid_user exists or not
        if valid_user
            #creates a session with username
            session[:user_id]=valid_user.userName
            session[:id]=valid_user.id
            session[:user_session]=valid_user.name
            session[:admin]= valid_user.admin
           
        #redirects the user to our private page.
            redirect_to :controller => 'map', :action => 'index'
        else
            flash[:notice] = "Invalid User/Password"
           redirect_to :action=> 'login'
        end
end
 
  def login

  end
 
  def private
  if !session[:user_id]
    redirect_to :action=> 'login'
    end
  end
 
  def logout
      if session[:user_id]
          reset_session
      end
      redirect_to :action=> 'login'
  end
 
end