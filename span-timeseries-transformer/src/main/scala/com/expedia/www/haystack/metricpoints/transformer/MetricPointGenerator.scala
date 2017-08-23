/*
 *  Copyright 2017 Expedia, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */
package com.expedia.www.haystack.metricpoints.transformer

import com.expedia.open.tracing.Span
import com.expedia.www.haystack.metricpoints.entities.MetricPoint
import com.expedia.www.haystack.metricpoints.entities.exceptions.MetricPointCreationException

import scala.util.{Failure, Success, Try}

trait MetricPointGenerator extends DurationMetricPointTransformer with TotalCountMetricPointTransformer with StatusCountMetricPointTransformer {

  def mapSpans(span: Span): Try[List[MetricPoint]] = {

    validate(span).map(span => mapSpan(span))
  }

  private def validate(span: Span): Try[Span] = {
    if (span.getProcess.getServiceName.isEmpty || span.getOperationName.isEmpty) {
      Failure(new MetricPointCreationException)
    } else {
      Success(span)
    }
  }


}




