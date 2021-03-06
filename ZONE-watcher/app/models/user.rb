class User < ActiveRecord::Base
  has_many :authentications
  # Include default devise modules. Others available are:
  # :token_authenticatable, :confirmable,
  # :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable, :omniauthable

  # Setup accessible (or protected) attributes for your model
  attr_accessible :email, :password, :password_confirmation, :remember_me, :provider, :uid, :login
  # attr_accessible :title, :body

  def self.find_for_provider_oauth(auth, signed_in_resource=nil)
    user = User.where(:provider => auth.provider, :uid => auth.uid).first
    unless user
      user = User.create(provider:auth.provider,
                           uid:auth.uid,
                           password:Devise.friendly_token[0,20]
                           )
    end
    user
  end
  
  def self.new_with_session(params, session)
    super.tap do |user|
      if data = session["devise.twitter_data"] && session["devise.twitter_data"]["extra"]["raw_info"]
        user.email = data["email"] if user.email.blank?
        user.provider = session["devise.twitter_data"].provider
        user.uid = session["devise.twitter_data"].uid
        user.password = Devise.friendly_token[0,20]
      elsif data = session["devise.github_data"] && session["devise.github_data"]["extra"]["raw_info"]
        user.email = data["email"] if user.email.blank?
        user.provider = session["devise.github_data"].provider
        user.uid = session["devise.github_data"].uid
        user.password = Devise.friendly_token[0,20]
      elsif data = session["devise.google_data"] && session["devise.google_data"]["extra"]["raw_info"]
        user.email = data["email"] if user.email.blank?
        user.provider = session["devise.google_data"].provider
        user.uid = session["devise.google_data"].uid
        user.password = Devise.friendly_token[0,20]
      end
    end
  end
end
