(ns photon-client.core
	(:require [muon-clojure.client :as cl] [clojure.core.async :as async :refer [go <! <!!]])
	)

(defn muon "creates a muon service with the specified name an optional tags" [x & tags] 
	(let [u (System/getenv "MUON_AMPQ_CONECTION_URL") mf (partial cl/muon-client u x "shim")]
    (if (seq tags) 
      (apply mf tags)
      (mf))
	))

(defn process-hot-channel 
  "Expects two functions cf a no arg to create the channel and hf a single arg to handle events placed on the channel" 
  [cf hf]
  (let [ch (cf)]
    (go (loop [elem (<! ch)] (hf elem) (recur (<! ch))))
    ))


(defn process-cold-channel 
	"Expects two functions cf a no arg to create the channel and hf a single arg to handle events placed on the channel" 
  [cf hf]
  (let [ch (cf)]
    (do
      (loop [ev (<!! ch)]
        (if (nil? ev)
                  (do (println "Cold stream processed"))
                  (do
                    (hf ev)
                    (recur (<!! ch))))))

  ))

(defn- subscription "creates a subscription from 0 for stream of type using the given muon client "[x t mu]
  (fn [] (cl/with-muon mu (cl/subscribe! "stream://photon/stream"
                        :from 0
                        :stream-type t
                        :stream-name x)))
  )

(defn hot-cold-subscription "creates a hot-cold-subscription from 0 for stream using the given muon client "[x mu]
	(subscription x "hot-cold" mu)
  )

(defn cold-subscription "creates a cold-subscription from 0 for stream using the given muon client "[x mu]
  (subscription x "cold" mu)
  )