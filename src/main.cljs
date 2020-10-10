(ns main
  (:require [reagent.core :as r]
            [cljs.core.async :refer (chan put! <! go go-loop timeout)]))


(def counter (r/atom 0))
(def username (r/atom ""))
(def password (r/atom ""))


(defn input-box [type label var]
  [:div {:class "input-box"}
   [:label label]
   [:input {:on-change #(reset! var (-> % .-target .-value)) :type type}]])

(defn login-box []
  (let [username (r/atom "")
        password (r/atom "")])
  [:div
   [input-box "text" "Username: " username]
   [input-box "password" "Password: " password]
   [:button.btn-blue.hover:bg-teal-400 {:on-click #(prn @username @password)} "press me"]])

(defn main-component []
  [:div
   [:h1.text-4xl.font-bold "This is a component"]
   [:h1.text-4xl.font-bold.p-4  {:class (if (< @counter 10)
                                          "bg-green-500"
                                          "bg-blue-800")
                                 :on-click #(swap! counter inc)} @counter]
   (into [:ol] (for [item (range @counter)]
                 [:li item]))
   [login-box]])

username


(defn mount [c]
  (r/render-component [c] (.getElementById js/document "app")))

(defn reload! []
  (mount main-component)
  (print "Hello reload!"))

(defn main! []
  (mount main-component)
  (print "Hello Main"))
