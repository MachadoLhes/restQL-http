(ns restql.server.response-util-test
    (:require [clojure.test :refer :all]
              [restql.server.response-util :refer :all]))

(def simple-response {:freight {:details {:success true :status 200 :metadata {:ignore-errors "ignore"} :headers {:max-age 600}}}})
(def mtplx-response {:freight {:details [{:success true :status 200 :metadata {:ignore-errors "ignore"} :headers {:max-age 600}}, {:success true :status 200 :metadata {:ignore-errors "ignore"} :headers {:max-age 125}}]}})
(def mtplx-one-empty-header {:freight {:details [{:success true :status 200 :metadata {:ignore-errors "ignore"} :headers {:max-age 600}}, {:success true :status 200 :metadata {:ignore-errors "ignore"} :headers {:max-age 125}}, {:success true :status 200 :metadata {:ignore-errors "ignore"} :headers {}}]}})

(deftest test-get-max-age-header
    (testing "if only one max-age, get-max-age-header should return max-age string"
        (is (= 
            50
            (get-max-age-header {:content-type "application/json", :max-age 50})))))

(deftest test-calculate-max-age-simple
    (testing "simple query with cache-control"
        (is (=
            600
            (calculate-max-age simple-response)))))    
        
(deftest test-calculate-max-age-multiplexed
    (testing "multiplexed query with cache-control"
        (is (=
            125
            (calculate-max-age mtplx-response)))))

(deftest test-calculate-max-age-multiplexed-with-empty-header
    (testing "multiplexed query with cache-control and one empty header"
        (is (=
            125
            (calculate-max-age mtplx-one-empty-header)))))