class FiltersController < ApplicationController
  include ApplicationHelper
  $endpoint = 'http://localhost:8890/sparql/'

  # GET /filters
  # GET /filters.json
  def index
    @filters = Filter.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @filters }
    end
  end

  # GET /filters/1
  # GET /filters/1.json
  def show
    @filter = Filter.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @filter }
    end
  end

  # GET /filters/new
  # GET /filters/new.json

  def getNumber
    @number = calculateNumber(params)
    respond_to do |format|
      format.html {render :inline => "<%= @number %>"}
    end
    
  end
  
  
end
