(ns main
  (:require [reagent.core :as r]
            [cljs.core.async :refer (chan put! <! go go-loop timeout)]))


;; ----------------------------------- STATE -----------------------------------


(def counter (r/atom 0))
(def username (r/atom ""))
(def password (r/atom ""))





;; ----------------------------------- COMPONENTS -----------------------------------

(defn input-box [type label var]
  [:div {:class "input-box"}
   [:label label]
   [:input {:on-change #(reset! var (-> % .-target .-value))   :type type}]])

(defn login-box []
  (let [username (r/atom "")
        password (r/atom "")])
  [:div
   [input-box "text" "Username: " username]
   [input-box "password" "Password: " password]
   [:button.btn-blue.hover:bg-teal-400 {:on-click #(prn @username @password)} "press me"]])

(defn navbar []
  [:div.flex.bg-black.w-full.text-white.p-2.mb-4
   [:a.m-2.px-3.py-2.border-2 {:href "#"} "Home"]
   [:a.m-2.px-3.py-2.border-2 {:href "#about"} "About"]
   [:a.m-2.px-3.py-2.border-2 {:href "#help"} "Help"]])

;; ----------------------------------- PAGES -----------------------------------

(defn about-page []
  [:div
   [navbar]
   [:h1.text.-4xl.font-bold "This about page"]])

(defn help-page []
  [:div
   [navbar]
   [:h1.text.-4xl.font-bold "This help page"]])

(defn main-page []
  [:div
   [navbar]
   [:h1.text-4xl.font-bold "This is a component"]
   [:h1.text-4xl.font-bold.p-4  {:class (if (< @counter 10)
                                          "bg-green-500"
                                          "bg-blue-800")
                                 :on-click #(swap! counter inc)} @counter]
   (into [:ol] (for [item (range @counter)]
                 [:li item]))
   [login-box]])

;; ----------------------------------- UTILITIES -----------------------------------å
;; 
;; 

(defn mount [c]
  (r/render-component [c] (.getElementById js/document "app")))

(def routes
  {"#about" about-page
   "#help" help-page
   "" main-page
   "default" about-page})

(defn handle-routes [routes event]
  (let [location (.-location.hash js/window)
        ;;pathname (.-location.pathname js/window)
        newpage (get routes location (get routes "default"))]
    (.history.replaceState js/window {} nil location)
    (mount newpage)))


(defn setup-router [routes]
  (.addEventListener js/window "hashchange" #(handle-routes routes %))
  (handle-routes routes nil))

(defn reload! []
  (setup-router routes)
  (print "Hello reload!"))

(defn main! []
  (setup-router routes)
  (print "Hello Main"))
